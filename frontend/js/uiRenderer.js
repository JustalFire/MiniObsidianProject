function renderAllNotes() {
    let notesLayer = document.getElementById('notes-layer');
    notesLayer.innerHTML = '';
    for (let i = 0; i < notes.length; i++) {
        renderNote(notes[i]);
    }
}

function renderNote(note) {
    let notesLayer = document.getElementById('notes-layer');
    let card = document.createElement('div');
    card.className = 'note-card';
    card.dataset.noteId = note.id;

    card.style.left = note.x + 'px';
    card.style.top = note.y + 'px';
    card.style.backgroundColor = note.color || DEFAULT_NOTE_COLOR;

    let titleDiv = document.createElement('div');
    titleDiv.className = 'note-title';
    titleDiv.textContent = note.title;

    let contentDiv = document.createElement('div');
    contentDiv.className = 'note-content';
    contentDiv.textContent = note.content;

    card.appendChild(titleDiv);
    card.appendChild(contentDiv);

    card.addEventListener('contextmenu', function(e) {
        e.preventDefault();
        e.stopPropagation();
        showMenuForNote(e.clientX, e.clientY, note.id);
    });

    card.addEventListener('mousedown', function(e) {
        if (e.button !== 0) {
            return;
        }
        if (isConnecting) {
            selectConnectionTarget(note.id);
            return;
        }
        startNoteDrag(e, note.id);
    });

    notesLayer.appendChild(card);
}

function removeNoteElement(noteId) {
    let el = document.querySelector('[data-note-id="' + noteId + '"]');
    if (el) {
        el.remove();
    }
}
