const menu = document.querySelector(".menu-bar i")
const main = document.querySelector("main")
menu.addEventListener("click", () => {
    main.classList.toggle("open")
})