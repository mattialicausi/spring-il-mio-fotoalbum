// VARIABILI

const domContainerList = document.getElementById('dom-container-list');
const BASE_URL = 'http://localhost:8080/api/v1/photos';
const BASE_URL_CONTACT = 'http://localhost:8080/api/contacts';
const formSearch = document.getElementById('form-search'); 
const contactForm = document.getElementById('contact-form');


// METODI

const getPhoto = async () => {
    const response = await fetch(BASE_URL);
    console.log(response);
    return response;
}

const createSingleItem = (item) => {

    return `
    <div class="col-4">
        <div class="card h-100" style="width: 18rem;">
            <img class="h-50" src="${item.url}" class="card-img-top" alt="${item.title}">
            <div class="card-body">
                <div class="d-flex align-items-center justify-content-between">
                    <h4 class="card-title">${item.title}</h4>
                </div>
                <p class="card-text">${item.description}.</p>
            </div>
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

function filterByTitle(photo) {
    const inputSearch = document.getElementById('input-search-name');
    const inputSearchValue = inputSearch.value;
  
    if (inputSearchValue) {
        const filteredList = photo.filter((element) =>
        element.title.toLowerCase().includes(inputSearchValue.toLowerCase())
      );
      console.log(filteredList);
      return filteredList;

    } else {
        loadAllPhoto();
    }
  }

  formSearch.addEventListener("submit", async (event) => {

    event.preventDefault();

    const allPhoto = await getPhoto();
    const allPhotoData = await allPhoto.json();
    const filteredList = filterByTitle(allPhotoData);
    domContainerList.innerHTML = createPhotoList(filteredList);
    console.log("fatto");
  });


  // metodi per inviare il form dei contatti

  const postContact = async (jsonData) => {
    const fetchOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: jsonData,
    };
    const response = await fetch(BASE_URL_CONTACT, fetchOptions);
    return response;
  };


  const saveContact = async (event) => {
    // prevent default
    event.preventDefault();
    // read input values
    const formData = new FormData(event.target);
    const formDataObj = Object.fromEntries(formData.entries());
    // VALIDATION
    // produce a json
    const formDataJson = JSON.stringify(formDataObj);
    // send ajax request
    const response = await postContact(formDataJson);
    // parse response
    const responseBody = await response.json();
    if (response.ok) {
      // reload data
      loadAllPhoto();
      // reset form
      event.target.reset();
      console.log("contatto salvato con successo")
    } else {
      // handle error
      console.log('ERROR');
      console.log(response.status);
      console.log(responseBody);
    }
  };



// RICHIAMO METODI

contactForm.addEventListener("submit", saveContact);

loadAllPhoto();