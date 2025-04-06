let lista_salas = document.getElementById("salas");

let salaSelecionada = null;
let nomeUsuario = null;
let stompClient = null;

async function carregarSalas() {
    try {
        const resposta = await fetch("http://localhost:8080/room");
        const salas = await resposta.json();

        salas.forEach(sala => {
            const li = document.createElement("li");
            li.textContent = sala.title + " ";

            const btn = document.createElement("button");
            btn.textContent = "Entrar";
            btn.onclick = () => abrirModalSenha(sala.title);

            li.appendChild(btn);
            lista_salas.appendChild(li);
        });
    } catch (erro) {
        console.error("Erro ao carregar salas:", erro);
    }
}

function abrirModalSenha(tituloSala) {
    salaSelecionada = tituloSala;
    document.getElementById("modal-title").textContent = `Entrar na sala: ${tituloSala}`;
    abrirModal("modalSenha");
}

function abrirModal(id) {
    document.getElementById(id).style.display = "flex";
}

function fecharModal(id) {
    document.getElementById(id).style.display = "none";
}

async function confirmarSenha() {
    const senha = document.getElementById("password-input").value;

    const payload = {
        title: salaSelecionada,
        password: senha
    };

    const resposta = await fetch("http://localhost:8080/room/validate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    const valido = await resposta.json();

    if (valido) {
        fecharModal("modalSenha");
        abrirModal("modalNome");
    } else {
        alert("Senha incorreta!");
    }
}

function confirmarNome() {
    nomeUsuario = document.getElementById("nome-usuario").value.trim();
    if (!nomeUsuario) return alert("Digite um nome válido!");

    fecharModal("modalNome");
    abrirModal("modalChat");

    document.getElementById("chat-room-title").textContent = salaSelecionada;

    conectarWebSocket();
}

function conectarWebSocket() {
    const socket = new SockJS("http://localhost:8080/chat");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe(`/topic/messages/${salaSelecionada}`, (mensagemRecebida) => {
            const mensagem = JSON.parse(mensagemRecebida.body);
            mostrarMensagem(`${mensagem.sender}: ${mensagem.content}`);
        });

        // Mensagem de entrada
        const mensagemEntrada = {
            sender: "🔔 Sistema",
            content: `${nomeUsuario} entrou na sala.`,
            roomTitle: salaSelecionada
        };
        stompClient.send(`/app/sendMessage/${salaSelecionada}`, {}, JSON.stringify(mensagemEntrada));
    });
}

function enviarMensagem() {
    const texto = document.getElementById("mensagem").value;
    if (!texto) return;

    const mensagem = {
        sender: nomeUsuario,
        content: texto,
        roomTitle: salaSelecionada
    };

    stompClient.send(`/app/sendMessage/${salaSelecionada}`, {}, JSON.stringify(mensagem));
    document.getElementById("mensagem").value = "";
}

function mostrarMensagem(texto) {
    const mensagensDiv = document.getElementById("mensagens");
    const p = document.createElement("p");
    p.textContent = texto;
    mensagensDiv.appendChild(p);
    mensagensDiv.scrollTop = mensagensDiv.scrollHeight;
}

function sairDoChat() {
    if (stompClient && stompClient.connected) {
        const mensagemSaida = {
            sender: "🔕 Sistema",
            content: `${nomeUsuario} saiu da sala.`,
            roomTitle: salaSelecionada
        };
        stompClient.send(`/app/sendMessage/${salaSelecionada}`, {}, JSON.stringify(mensagemSaida));
        stompClient.disconnect(() => {
            console.log("Desconectado do WebSocket.");
        });
    }

    fecharModal("modalChat");
    document.getElementById("mensagens").innerHTML = "";
}

window.addEventListener("beforeunload", () => {
    sairDoChat();
});

carregarSalas();