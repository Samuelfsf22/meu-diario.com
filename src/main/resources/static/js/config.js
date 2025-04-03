document.getElementById("btnMudar").addEventListener("click", (e) =>{
    console.log("ta funcionando");

    var nomeArquivo = document.getElementById("inputNomeArquivo").value;
    var caminhoArquivo = document.getElementById("inputCaminhoArquivo").value;

    if(nomeArquivo == "" || caminhoArquivo == ""){
        alert("Você Precisa Inserir Algo!");
        e.preventDefault();
    };

    console.log(nomeArquivo);
    console.log(caminhoArquivo);
});