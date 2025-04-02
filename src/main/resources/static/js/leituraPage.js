function autoResize(textarea) {
    // Reseta a altura para calcular o ajuste corretamente
    textarea.style.height = 'auto';
    
    // Ajusta a altura de acordo com o conteúdo
    textarea.style.height = (textarea.scrollHeight) + 'px';
}

// Ajustar a altura do textarea ao carregar a página
window.addEventListener('DOMContentLoaded', (event) => {
    var textarea = document.getElementById('campoTexto');
    autoResize(textarea); // Ajusta a altura quando a página carrega
});