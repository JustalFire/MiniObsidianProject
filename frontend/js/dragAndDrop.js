function initDragAndDrop() {
    document.addEventListener('mousemove', onDocumentMouseMove);
    document.addEventListener('mouseup', onDocumentMouseUp);
}

function startNoteDrag(event, noteId) {
    if (isConnecting) {
        return;
    }

    let noteElement = document.querySelector('[data-note-id="' + noteId + '"]');
    if (!noteElement) {
        return;
    }

    let rect = noteElement.getBoundingClientRect();
    dragOffsetX = event.clientX - rect.left;
    dragOffsetY = event.clientY - rect.top;
    dragStartScreenX = event.clientX;
    dragStartScreenY = event.clientY;
    dragNoteId = noteId;
    isDragging = true;
    hasMovedDuringDrag = false;

    noteElement.classList.add('dragging');
    event.preventDefault();
}

function onDocumentMouseMove(event) {
    if (!isDragging || !dragNoteId) {
        return;
    }

    let dx = Math.abs(event.clientX - dragStartScreenX);
    let dy = Math.abs(event.clientY - dragStartScreenY);
    if (dx > 4 || dy > 4) {
        hasMovedDuringDrag = true;
    }

    let noteElement = document.querySelector('[data-note-id="' + dragNoteId + '"]');
    if (!noteElement) {
        return;
    }

    let container = document.getElementById('canvas-container');
    let newX = event.clientX - dragOffsetX + container.scrollLeft;
    let newY = event.clientY - dragOffsetY + container.scrollTop;

    if (newX < 0) newX = 0;
    if (newY < 0) newY = 0;

    noteElement.style.left = newX + 'px';
    noteElement.style.top = newY + 'px';

    let note = findNoteById(dragNoteId);
    if (note) {
        note.x = newX;
        note.y = newY;
    }

    redrawAllLines();
}

async function onDocumentMouseUp() {
    if (!isDragging || !dragNoteId) {
        return;
    }

    let currentNoteId = dragNoteId;
    let moved = hasMovedDuringDrag;

    let noteElement = document.querySelector('[data-note-id="' + currentNoteId + '"]');
    if (noteElement) {
        noteElement.classList.remove('dragging');
    }

    let note = findNoteById(currentNoteId);
    isDragging = false;
    dragNoteId = null;

    if (!moved && note) {
        openViewModal(note);
        return;
    }

    if (note) {
        await apiUpdateNote(note.id, {
            title: note.title,
            content: note.content,
            x: note.x,
            y: note.y,
            color: note.color
        });
    }
}
