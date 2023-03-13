var select = document.querySelector('#post');
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
        console.log("2")
    };
    if( selectedId == 'student' ) {
        worker.style = 'display: none'
        student.style = 'display: block'
        console.log("1")
    };
};
