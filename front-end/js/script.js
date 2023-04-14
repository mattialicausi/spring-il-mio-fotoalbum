// VARIABILI

const domContainerList = document.getElementById('dom-container-list');
const BASE_URL = 'http://localhost:8080/api/v1/photos';

// METODI

const getPhoto = async () => {
    const response = await fetch(BASE_URL);
    console.log(response);
    return response;
}

const createSingleItem = (item) => {

    return `
    <div class="card h-100 col-4" style="width: 18rem;">
        <img class="h-50" src="${item.url}" class="card-img-top" alt="${item.title}">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between">
                <h5 class="card-title">${item.title}</h5>
            </div>
            <p class="card-text">${item.description}.</p>
        </div>
    </div>
    
    `;

}

  
const createPhotoList = (data) => {

    let list = "";

    data.forEach((element) => {
        list += createSingleItem(element);
    })

    return list;

}

const loadAllPhoto = async () => {
    const response = await getPhoto();

    if (response.ok) {
        const data = await response.json();
        const filteredResponse = data.filter((element) => element.visible === true);
        domContainerList.innerHTML = createPhotoList(filteredResponse);
    } else {
        console.log("ERROR");
    }
}


// RICHIAMO METODI

loadAllPhoto();