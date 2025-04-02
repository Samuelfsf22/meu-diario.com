var cooldownDoBtnDarkMode = false;
var elementos = document.querySelectorAll('*');
var mode;

document.addEventListener('DOMContentLoaded', () =>{
    console.log("Modo Atual = " + recuperarMode(mode));
    if(recuperarMode(mode) == 'true'){
      console.log("entrou no if")
      mudarParaDark();
    } else{
      console.log("Não Entrou no if")
      mudarParaWhite();
    }
});

document.getElementById("btnDarkMode").addEventListener("click", () =>{
    SistemaDarkMode();
  });
  
  function SistemaDarkMode(){
    
    var darkmode = analisarMode();
  
    if(cooldownDoBtnDarkMode == false){
      cooldownDoBtnDarkMode = true;
  
      setTimeout(function () {
        cooldownDoBtnDarkMode = false;
      }, 1000);
    
      if(darkmode == false){
        darkmode = true;
        mudarParaDark();
        console.log(darkmode);
      } else{
        darkmode = false;
        mudarParaWhite();
        console.log(darkmode);
      };
    }
  
    console.log("Modo Atual: " + recuperarMode(mode));
  };
  
  function mudarParaDark(){
    elementos.forEach(function(elemento){
      elemento.classList.remove("transicao");
      elemento.classList.add("transicao");
      elemento.classList.add("darkMode");
      guardarMode(mode, true);
    });
  };
  
  function mudarParaWhite(){
    elementos.forEach(function(elemento){
      elemento.classList.remove("transicao");
      elemento.classList.add("transicao");
      elemento.classList.remove("darkMode");
      guardarMode(mode, false);
  });
  };
  
  function analisarMode(){
    for(var i = 0; i < elementos.length; i++){
      if(!elementos[i].classList.contains("darkMode")){
        return false;
      };
    }
  
    return true;
  };
  
  function guardarMode(mode, status){
    localStorage.setItem(mode, status);
  };
  
  function recuperarMode(mode){
    return localStorage.getItem(mode);
  };