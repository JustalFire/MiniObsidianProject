let lastClickCanvasX = 200;
let lastClickCanvasY = 200;

function initContextMenu() {
    let menuElement = document.getElementById('context-menu');

    document.addEventListener('click', function() {
        hideContextMenu();
    });

    menuElement.addEventListener('click', function(e) {
        e.stopPropagation();
    });

    let canvasContainer = document.getElementById('canvas-container');
    canvasContainer.addEventListener('contextmenu', function(e) {
        e.preventDefault();
        let canvasX = e.clientX + canvasContainer.scrollLeft;
        let canvasY = e.clientY + canvasContainer.scrollTop;
        showMenuForCanvas(e.clientX, e.clientY, canvasX, canvasY);
    });

    document.getElementById('menu-create').addEventListener('click', onMenuCreate);
    document.getElementById('menu-connect').addEventListener('click', onMenuConnect);
    document.getElementById('menu-edit').addEventListener('click', onMenuEdit);
    document.getElementById('menu-delete').addEventListener('click', onMenuDelete);
}

function showMenuForNote(screenX, screenY, noteId) {
    let container = document.getElementById('canvas-container');
    let menuElement = document.getElementById('context-menu');
    lastClickCanvasX = screenX + container.scrollLeft;
    lastClickCanvasY = screenY + container.scrollTop;
    activeNoteId = noteId;

    document.getElementById('menu-create').style.display = 'block';
    document.getElementById('menu-connect').style.display = 'block';
    document.getElementById('menu-edit').style.display = 'block';
    document.getElementById('menu-delete').style.display = 'block';

    menuElement.style.left = screenX + 'px';
    menuElement.style.top = screenY + 'px';
    menuElement.style.display = 'block';
}

function showMenuForCanvas(screenX, screenY, canvasX, canvasY) {
    let menuElement = document.getElementById('context-menu');
    lastClickCanvasX = canvasX;
    lastClickCanvasY = canvasY;
    activeNoteId = null;

    document.getElementById('menu-create').style.display = 'block';
    document.getElementById('menu-connect').style.display = 'none';
    document.getElementById('menu-edit').style.display = 'none';
    document.getElementById('menu-delete').style.display = 'none';

    menuElement.style.left = screenX + 'px';
    menuElement.style.top = screenY + 'px';
    menuElement.style.display = 'block';
}

function hideContextMenu() {
    document.getElementById('context-menu').style.display = 'none';
}

function onMenuCreate() {
    hideContextMenu();
    openCreateModal(lastClickCanvasX, lastClickCanvasY);
}

function onMenuConnect() {
    hideContextMenu();
    if (activeNoteId) {
        startConnectionMode(activeNoteId);
    }
}

function onMenuEdit() {
    hideContextMenu();
    let note = findNoteById(activeNoteId);
    if (note) {
        openEditModal(note);
    }
}

async function onMenuDelete() {
    hideContextMenu();
    let noteId = activeNoteId;
    if (!noteId) {
        return;
    }
    await apiDeleteNote(noteId);
    removeNoteFromState(noteId);
    removeNoteElement(noteId);
}
