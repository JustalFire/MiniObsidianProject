let notes = [];
let links = [];
let activeNoteId = null;
let isConnecting = false;
let sourceNoteId = null;
let isDragging = false;
let hasMovedDuringDrag = false;
let dragStartScreenX = 0;
let dragStartScreenY = 0;
let dragOffsetX = 0;
let dragOffsetY = 0;
let dragNoteId = null;
let createPositionX = 200;
let createPositionY = 200;
let editingNoteId = null;

function findNoteById(id) {
    for (let i = 0; i < notes.length; i++) {
        if (notes[i].id === id) {
            return notes[i];
        }
    }
    return null;
}

function removeNoteFromState(noteId) {
    let result = [];
    for (let i = 0; i < notes.length; i++) {
        if (notes[i].id !== noteId) {
            result.push(notes[i]);
        }
    }
    notes = result;
}

function updateNoteInState(updatedNote) {
    for (let i = 0; i < notes.length; i++) {
        if (notes[i].id === updatedNote.id) {
            notes[i] = updatedNote;
            return;
        }
    }
}
