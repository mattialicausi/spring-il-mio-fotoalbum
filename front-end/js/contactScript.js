// VARIABILI

const contactForm = document.getElementById('contact-form');
const BASE_URL_CONTACT = 'http://localhost:8080/api/contacts';

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
    //   loadAllPhoto();
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