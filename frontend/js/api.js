async function fetchAllNotes() {
    let response = await fetch('/api/notes');
    return await response.json();
}

async function fetchAllLinks() {
    let response = await fetch('/api/links');
    return await response.json();
}

async function apiCreateNote(noteData) {
    let response = await fetch('/api/notes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(noteData)
    });
    return await response.json();
}

async function apiUpdateNote(id, noteData) {
    let response = await fetch('/api/notes/' + id, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(noteData)
    });
    return await response.json();
}

async function apiDeleteNote(id) {
    await fetch('/api/notes/' + id, {
        method: 'DELETE'
    });
}

async function apiCreateLink(linkData) {
    let response = await fetch('/api/links', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(linkData)
    });
    return await response.json();
}

async function apiDeleteLink(id) {
    await fetch('/api/links/' + id, {
        method: 'DELETE'
    });
}
