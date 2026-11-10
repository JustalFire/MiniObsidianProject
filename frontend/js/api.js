const API_URL = window.location.protocol === 'file:' ? 'http://localhost:8080' : '';

async function fetchAllNotes() {
    let response = await fetch(API_URL + '/api/notes');
    return await response.json();
}

async function fetchAllLinks() {
    let response = await fetch(API_URL + '/api/links');
    return await response.json();
}

async function apiCreateNote(noteData) {
    let response = await fetch(API_URL + '/api/notes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(noteData)
    });
    return await response.json();
}

async function apiUpdateNote(id, noteData) {
    let response = await fetch(API_URL + '/api/notes/' + id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(noteData)
    });
    return await response.json();
}

async function apiDeleteNote(id) {
    await fetch(API_URL + '/api/notes/' + id, {
        method: 'DELETE'
    });
}

async function apiCreateLink(linkData) {
    let response = await fetch(API_URL + '/api/links', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(linkData)
    });
    return await response.json();
}

async function apiDeleteLink(id) {
    await fetch(API_URL + '/api/links/' + id, {
        method: 'DELETE'
    });
}
