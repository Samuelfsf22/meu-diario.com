var cooldownDoBtnDarkMode = false;
var elementos = document.querySelectorAll('*');
var mode;

const iconI = document.querySelector(".iconeDark");
const iconII = document.querySelector(".iconeLight");

document.addEventListener('DOMContentLoaded', () =>{
    if(recuperarMode(mode) == 'true'){
      mudarParaDark();
      iconI.style.display = "none";
      iconII.style.display = "inline";
    } else{
      mudarParaWhite();
      iconI.style.display = "inline";
      iconII.style.display = "none";
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
        iconI.style.display = "none";
        iconII.style.display = "inline";
      } else{
        darkmode = false;
        mudarParaWhite();
        iconI.style.display = "inline";
        iconII.style.display = "none";
      };
    }
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