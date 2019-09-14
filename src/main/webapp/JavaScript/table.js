/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global fetch */

const table = document.getElementById("memberTable");
const reloadMembersButton = document.getElementById("reloadMembers");

reloadMembersButton.onclick = function (e)
{
    e.preventDefault();
    populateTable();
};

function populateTable()
{
    let urlAll = "/CA1/api/groupmembers/all";

    fetch(urlAll)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
        
                let memberTable = data.map(n => "<tr><td>" + n.id + "</td><td>" + n.firstname
                            + "</td><td>" + n.lastname + "</td><td>" + n.color + "</td><td>" + n.email + "</td></tr>");
                memberTable.unshift("<table><tr><th>id#</th><th>Firstname</th><th>Lastname</th><th>Color</th><th>Email</th></tr>");
                memberTable.push("</table");
                memberTable = memberTable.join('');
                table.innerHTML = memberTable;
            });
}