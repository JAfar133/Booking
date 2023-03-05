const dateControl = document.querySelector('input[type="date"]');
const timeStartControl = document.querySelector('input[name="timeStart"]');
const timeEndControl = document.querySelector('input[name="timeEnd"]');
let currentDate = new Date();
let minutes = currentDate.getMinutes();
let hours = currentDate.getHours();
if((hours >= 0 && hours < 8)){
    hours = 8;
    minutes = '30';
    currentDate = new Date(currentDate.getUTCFullYear(),currentDate.getUTCMonth(),currentDate.getUTCDay()-1);
}
if ((hours >= 22 && minutes>0) || hours == 23){
    hours = 8;
    minutes = '30';
    currentDate = new Date(currentDate.getUTCFullYear(),currentDate.getUTCMonth(),currentDate.getUTCDay());
}
const dateForm = dateFormat(currentDate,"yyyy-mm-dd");
if(minutes % 10 != 0) {
    if(minutes % 10 < 5){
        minutes-=(minutes % 10);
    }
    else minutes+=(10 - (minutes % 10));
    if(minutes == 60) {
        minutes = 0+"0";
        hours+=1;
    }
    if(minutes == 0) {
        minutes = 0+"0";
    }
}
if(hours<10) hours = '0'+hours;

dateControl.min = dateForm;
dateControl.value = dateForm;
timeStartControl.value = hours + ":" + minutes;
timeEndControl.value = "22:00";