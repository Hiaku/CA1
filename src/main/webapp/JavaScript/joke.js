/* global fetch */

//let url = "https://programming4me.com/CA1/api/joke/all";
//let url = "http://localhost:8080/CA1/api/joke/all";

const table = document.getElementById("tableTest");
let url = "/CA1/api/joke/all";


//let cars = [
//    {id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000},
//    {id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900},
//    {id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000},
//    {id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799},
//    {id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799}
//];
//
//function makeCarTable() {
//    let carsTable = cars.map(n => "<tr><td>" + n.id + 
//            "</td><td>" + n.year + "</td><td>" + n.make + 
//            "</td><td>" + n.model + "</td><td>" + n.price + "</td></tr>");
//    carsTable.unshift("<table><tr><th>id</th><th>year</th><th>make</th><th>model</th><th>price</th></tr>");
//    carsTable.push("</table");
//    carsTable = carsTable.join('');
//    table.innerHTML = carsTable;
//}
//
//makeCarTable();

fetch(url)
        .then(res => res.json())
        .then(data => {
            // Inside this callback, and only here, the response data is available
            console.log("data", data);
            /* data now contains the response, converted to JavaScript
             Observe the output from the log-output above
             Now, just build your DOM changes using the data*/

//            let jokeTable = JSON.parse(data);
//            let jokeTable = data.result;
//            let jokeTable = data.entries();
//            let jokeTable = data.toString();
            let jokeTable = data;
            jokeTable.map(n => "<tr><td>" + n.id + "</td><td>" + n.title
                        + "</td><td>" + n.reference + "</td><td>" + n.type + "</td></tr>");
            jokeTable.unshift("<table><tr><th>id#</th><th>Title</th><th>Reference</th><th>Type</th></tr>");
            jokeTable.push("</table");
            jokeTable = jokeTable.join('');
            return table.innerHTML = jokeTable;

            console.log("data", data);

        })

        