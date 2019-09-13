const paragraph = document.getElementById("");
const jokeId = document.getElementById("");
let url = "/CA1/api/joke/" + jokeId;


jokeId.onclick = function(e)
{
    e.preventDefault();
}