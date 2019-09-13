/* global fetch */

const table = document.getElementById("jokeTable");
const jokeSingleButton = document.getElementById("showSingleButton");
const jokeAllButton = document.getElementById("showAllButton");
const jokeRandomButton = document.getElementById("showRandomButton");
const paragraph = document.getElementById("paragraph");



jokeSingleButton.onclick = function (e)
{
    e.preventDefault();
    const jokeInput = document.getElementById("jokeInput").value;
    let urlSingle = "/CA1/api/joke/" + jokeInput;

    fetch(urlSingle)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                paragraph.innerHTML = data.title + "\n" + data.body;
            })
    table.innerHTML = "";
}

jokeRandomButton.onclick = function (e)
{
    e.preventDefault();
    const jokeInput = document.getElementById("jokeInput").value;
    let urlRandom = "/CA1/api/joke/random";

    fetch(urlRandom)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                paragraph.innerHTML = data.title + "\n" + data.body;
            })
    table.innerHTML = "";
}

jokeAllButton.onclick = function (e)
{
    e.preventDefault();
    let urlAll = "/CA1/api/joke/all";

    fetch(urlAll)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
        
                let jokeTable = data.map(n => "<tr><td>" + n.id + "</td><td>" + n.title
                            + "</td><td>" + n.reference + "</td><td>" + n.type + "</td></tr>");
                jokeTable.unshift("<table><tr><th>id#</th><th>Title</th><th>Reference</th><th>Type</th></tr>");
                jokeTable.push("</table");
                jokeTable = jokeTable.join('');
                table.innerHTML = jokeTable;
            });
            paragraph.innerHTML = "";
}