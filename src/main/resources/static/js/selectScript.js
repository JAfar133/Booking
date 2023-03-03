var select = document.querySelector('select');
var student = document.querySelector('.student')
var worker = document.querySelector('.worker')

select.onchange = function() {
    var indexSelected = select.selectedIndex,
        option = select.querySelectorAll('option')[indexSelected];

    var selectedId = option.getAttribute('id');

    if( selectedId == 'student' ) {
        worker.style = 'display: none'
        student.style = 'display: flex'
        console.log("1")
    };
    if( selectedId == 'worker' ) {
        student.style = 'display: none'
        worker.style = 'display: block'
        console.log("2")
    };
};