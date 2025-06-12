// Função para ajustar a altura do textarea com base no conteúdo
const textarea = document.getElementById('campoTexto');
        
textarea.addEventListener('input', function () {
  // Redefine a altura para 'auto' antes de calcular a nova altura
  this.style.height = 'auto';
  
  // Ajusta a altura do textarea para caber o conteúdo
  this.style.height = (this.scrollHeight) + 'px';
});

const inputRadioAuto = document.getElementById('dataHoraAutomatico');
const inputRadioManual = document.getElementById('dataHoraManual');
const divDataManual = document.querySelector(".divDataManual");

inputRadioAuto.addEventListener("click", (e) =>{
    console.log(e.target.checked);
    if(e.target.checked){
        divDataManual.classList.add("sumir");
    }
});

inputRadioManual.addEventListener("click", (e) =>{
    console.log(e.target.checked);
    if(e.target.checked){
        divDataManual.classList.remove("sumir");
    }
});