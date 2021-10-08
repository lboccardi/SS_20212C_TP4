var color = '#00d5ff'
var selected_color = '#f05800'
var close_color = '#8aff78'
var main_color = '#f5f5f5'
var close_circles = []
var solarSistemSize = 1e9 * 1000
var canvasPixelSize = 1000
var scale = solarSistemSize/canvasPixelSize
var invScale = 1/scale
var mustDrawGrid = false
var mustDrawRc = false
var mustColor = false
var drawArrows = false
var paused = false

                //ship     earth      mars      sun
const colors = ['#fffc3b','#3b68ff','#ff5b3b','#ebebeb',]
const radius = [    5,      15,         10,     30]

class Circle {
    constructor(id, x, y, r) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    draw() {
        c.beginPath();
        c.arc(this.x, this.y, this.r, 0, Math.PI * 2, false);
        c.fillStyle = colors[this.id]; 
        c.fill();

        c.font = `${this.r * 0.7}pt Calibri`;
        c.fillStyle = '#000000';
        c.textAlign = 'center';
        c.fillText(this.id, this.x, this.y+3);
    }

    update(x, y) {
        this.x = x
        this.y = y
        this.draw();
    }
}

function isInside(id, circle_x, circle_y, rad, x, y)
{
    if (((x - circle_x) * (x - circle_x) + (y - circle_y) * (y - circle_y)) <= (rad * rad))
        return true;
    else
        return false;
}

//---------------Set up Canvas----------------

var canvas = document.querySelector('canvas');
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;
var c = canvas.getContext('2d'); 

//----------------buttons-----------------------

document.getElementById('plus-btn').addEventListener("click", function() {
    framerate = framerate * 2;
    frameSize =  1/framerate;
    init();
});

document.getElementById('minus-btn').addEventListener("click", function() {
    framerate = framerate / 2;
    frameSize =  1/framerate;
    init();
});

document.getElementById('restart_sim_btn').addEventListener("click", function() {
    init()
});

document.getElementById('toggle_pause_btn').addEventListener("click", function() {
    paused = !paused
});

document.getElementById('submit_scale_btn').addEventListener('click', function() {
    scale = document.getElementById('scale_input').value
    init()
});

//-------------draw circles---------------------

let simulation = []

document.getElementById('inputfile')
    .addEventListener('change', function() {
        var fr=new FileReader();
        fr.onload=function(){
            simulation = JSON.parse(fr.result);
            init()
        }
        fr.readAsText(this.files[0]);
    })

var circleArray = [];

let frames = 1
let curr_frame = 0
let requestID = null
let framerate = 10000
let frameSize =  1/framerate
let time = 0
let lastTime = time
let valid = true;

function init(){

    document.getElementById('speed-input').value = framerate;
    
    if(requestID){
        window.cancelAnimationFrame(requestID);
    }
    
    console.log(simulation)

    circleArray = [];

    frames = simulation.events.length-1
    curr_frame = 0

    canvas.width = solarSistemSize * invScale;
    canvas.height = solarSistemSize * invScale;

    let i = 0;
    for (circle of simulation.events[curr_frame].circles){
        circleArray.push(new Circle(circle.id, (circle.x*invScale + canvas.width/2), (circle.y*invScale + canvas.height/2)  , radius[i]));
        i++;
    }

    console.log(circleArray[2].x, circleArray[2].y)

    for(circle of circleArray){
        circle.draw()
    }

    if(frames > 1)
        animate();
}

function animate(){
    c.clearRect(0,0,canvas.width, canvas.height);
    // c.fillStyle = 'rgba(58, 58, 58, .05)';
    // c.fillRect(0, 0, canvas.width, canvas.height);

    console.log(circleArray[2].x, circleArray[2].y)

    if(paused){
        for(let j=0; j<simulation.events[curr_frame].circles.length;j++){
            let currCircle = simulation.events[curr_frame].circles[j]
            circleArray[j].update((currCircle.x*invScale + canvas.width/2), (currCircle.y*invScale + canvas.height/2))
        }
        requestID = requestAnimationFrame(animate);
    } else{
        while(time < lastTime + frameSize){
            if(curr_frame < simulation.events.length-1){
                time += simulation.events[curr_frame].t
                curr_frame++
                frames--
            }else{
                valid = false
                break
            }
        }
        lastTime = time
    
        if(valid){
            for(let j=0; j<simulation.events[curr_frame].circles.length;j++){
                let currCircle = simulation.events[curr_frame].circles[j]
                circleArray[j].update((currCircle.x*invScale + canvas.width/2), (currCircle.y*invScale + canvas.height/2))
            }
        }
    
        //console.log('Time: ', time)
    
        if(frames > 0){
            requestID = requestAnimationFrame(animate);
        }else{
            frames = simulation.events.length-1;
            curr_frame = 0;
            time = 0
            lastTime = 0
            requestID =requestAnimationFrame(animate);
            valid = true
        }
    }
}