function onSubmit() {
    const url = "http://localhost:8080/users/create";
    const data = {
        email: document.getElementById("email").value,
        name: document.getElementById("name").value,
        password: document.getElementById("password").value
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                document.getElementById("errorAlert").hidden = false;
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

    // window.location.replace("http://localhost:8080/login");
}