function startConnectionMode(noteId) {
    isConnecting = true;
    sourceNoteId = noteId;
}

async function selectConnectionTarget(targetNoteId) {
    if (!isConnecting) {
        return;
    }
    let newLink = await apiCreateLink({
        sourceNoteId: sourceNoteId,
        targetNoteId: targetNoteId
    });
    links.push(newLink);
    redrawAllLines();
    isConnecting = false;
    sourceNoteId = null;
}
