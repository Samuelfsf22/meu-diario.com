document.querySelectorAll(".btnLer").forEach(btn =>{
    btn.addEventListener("click", (e) =>{

        const id = btn.id;
        window.location.href = `/registro/${id}`;
    });
});

document.querySelectorAll(".btnEditar").forEach(btn =>{
    btn.addEventListener("click", (e) =>{

        const id = btn.id;
        window.location.href = `/registro/editar/${id}`;
    });
});

document.querySelectorAll(".btnExcluir").forEach(btn =>{
    btn.addEventListener("click", (e) =>{
        console.log(e.target);
        const id = btn.id;

        fetch(`/registro/${id}`, {
          method: 'DELETE'
        })
        .then(response => {
          if (response.ok) {
            alert('Registro deletado com sucesso!');
              window.location.href = `/leitura`;
          } else {
            alert('Erro ao deletar o registro.');
          }
        })
        .catch(error => {
          console.error('Erro na requisição:', error);
        });
    });
});