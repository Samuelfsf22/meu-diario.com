// Função para ajustar a altura do textarea com base no conteúdo
const textarea = document.getElementById('campoTexto');
        
textarea.addEventListener('input', function () {
  // Redefine a altura para 'auto' antes de calcular a nova altura
  this.style.height = 'auto';
  
  // Ajusta a altura do textarea para caber o conteúdo
  this.style.height = (this.scrollHeight) + 'px';
});