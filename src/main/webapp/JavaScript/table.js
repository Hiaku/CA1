/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let getPosts = document.getElementById("getPosts").addEventListener()('click', getPosts);

var members = [
    
];


// START OF TABLE CREATION

// MAKES THE START OF THE TABLE. 
function tableHeaderStart() {
    return "<table class=\"table\"><thead><tr>";
}

// END OF TABLE HEADER
function tableHeaderEnd() {
    return "</tr></thead><tbody>";
}

// MAKES A SINGLE TABLE HEADER.
function tableHeader(header) {
    return "<th scope=\"col\">" + header + "</th>";
}

// MAKES A SINGLE TABLE ROW.
function tableRow(member) {
    let returnString = "<tr>";
    Object.values(member).forEach(element => returnString += "<td>" + element + "</td>");
    return returnString + "</tr>";
}

// RETURNS AN ARRAY OF OBJECTS AS A TABLE.
let membermap = function (members) {
    // TABLE HEADER
    let returnString = tableHeaderStart();
    Object.keys(members[0]).forEach(element => returnString += tableHeader(element));
    returnString += tableHeaderEnd();

    // TABLE ROWS
    members.forEach(element => returnString += tableRow(element));

    // Ending table:
    returnString += "</tbody></table>";

    return returnString;
};
document.getElementById("MembersTable").innerHTML = membermap(members);
// END OF TABLE CREATION FUNCTIONS
