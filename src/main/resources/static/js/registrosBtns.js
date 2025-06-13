document.querySelectorAll(".btnLer").forEach(btn =>{
    btn.addEventListener("click", (e) =>{
        console.log(e.target);

        const id = btn.id;
        window.location.href = `/registro/${id}`;
    });
});