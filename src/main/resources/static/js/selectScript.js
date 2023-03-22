var select = document.querySelector('#post');
var structure = document.querySelector('#structure');
var institute = document.querySelector('#institute');
var student = document.querySelector('.student')
var worker = document.querySelector('.worker')
worker.style = 'display: none'
select.onchange = function() {
    var indexSelected = select.selectedIndex,
        option = select.querySelectorAll('option')[indexSelected];

    var selectedId = option.getAttribute('id');

    if( selectedId == 'worker' ) {
        student.style = 'display: none'
        worker.style = 'display: block'
        institute.required = false
        structure.required = true
        console.log("2")
    };
    if( selectedId == 'student' ) {
        worker.style = 'display: none'
        student.style = 'display: block'
        structure.required = false
        institute.required = true
        console.log("1")
    };
};
