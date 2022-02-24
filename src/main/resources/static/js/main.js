const menu = document.querySelector(".menu-bar")
const main = document.querySelector("main")
menu.addEventListener("click", () => {
    main.classList.toggle("open")
})