async function loadNotes() {
    notes = await fetchAllNotes();
    renderAllNotes();
}

async function loadLinks() {
    links = await fetchAllLinks();
    redrawAllLines();
}

document.addEventListener('DOMContentLoaded', async function() {
    initTelegramWebApp();
    initModals();
    initContextMenu();
    initDragAndDrop();

    await loadNotes();
    await loadLinks();
});
