new Vue({
    el: "#app",
    methods: {
        getDivValue() {
            let divValue = event.target.textContent;
            console.log("Значение div:", divValue);
        }
    }
});