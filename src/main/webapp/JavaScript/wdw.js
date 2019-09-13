/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// MAKE LIST
let wdw = document.getElementById("mydivtext");

let people = ["Martin", "Nikolaj"];
let what = ["Jokes and corresponding frontend", "First and second assignment"]

function makeList() {

    let text = people.map(person => "<li>" + person + "</li>");

    text.unshift("<ul>");
    text.push("</ul>");

    wdw.innerHTML = text.join("");
}
makeList();
// MAKE LIST END
