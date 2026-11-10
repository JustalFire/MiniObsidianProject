function initModals() {
    document.getElementById('modal-save').addEventListener('click', saveModal);
    document.getElementById('modal-cancel').addEventListener('click', closeModal);
    document.getElementById('edit-modal-overlay').addEventListener('click', closeModal);

    document.getElementById('view-modal-close').addEventListener('click', closeViewModal);
    document.getElementById('view-modal-overlay').addEventListener('click', closeViewModal);
}

function openViewModal(note) {
    document.getElementById('view-modal-title').textContent = note.title;
    document.getElementById('view-modal-body').textContent = note.content;
    document.getElementById('view-modal').style.display = 'block';
}

function closeViewModal() {
    document.getElementById('view-modal').style.display = 'none';
}

function openEditModal(note) {
    editingNoteId = note.id;
    document.getElementById('note-modal-heading').textContent = 'Редактирование заметки';
    document.getElementById('modal-title').value = note.title;
    document.getElementById('modal-content').value = note.content;
    document.getElementById('modal-color').value = note.color || DEFAULT_NOTE_COLOR;
    document.getElementById('note-modal').style.display = 'block';
}

function openCreateModal(x, y) {
    editingNoteId = null;
    createPositionX = x;
    createPositionY = y;
    document.getElementById('note-modal-heading').textContent = 'Создание заметки';
    document.getElementById('modal-title').value = '';
    document.getElementById('modal-content').value = '';
    document.getElementById('modal-color').value = DEFAULT_NOTE_COLOR;
    document.getElementById('note-modal').style.display = 'block';
}

function closeModal() {
    document.getElementById('note-modal').style.display = 'none';
    editingNoteId = null;
}

async function saveModal() {
    let title = document.getElementById('modal-title').value;
    let content = document.getElementById('modal-content').value;
    let color = document.getElementById('modal-color').value;

    if (editingNoteId) {
        let note = findNoteById(editingNoteId);
        let updated = await apiUpdateNote(editingNoteId, {
            title: title,
            content: content,
            x: note.x,
            y: note.y,
            color: color
        });
        updateNoteInState(updated);
        renderAllNotes();
        redrawAllLines();
    } else {
        let created = await apiCreateNote({
            title: title,
            content: content,
            x: createPositionX,
            y: createPositionY,
            color: color
        });
        notes.push(created);
        renderNote(created);
    }

    closeModal();
}
