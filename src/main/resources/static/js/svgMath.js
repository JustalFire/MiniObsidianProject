function getNoteCenterFromData(note) {
    return {
        x: note.x + NOTE_WIDTH / 2,
        y: note.y + NOTE_MIN_HEIGHT / 2
    };
}

function createSvgLine(x1, y1, x2, y2, linkId) {
    let svgNS = 'http://www.w3.org/2000/svg';
    let line = document.createElementNS(svgNS, 'line');
    line.setAttribute('x1', x1);
    line.setAttribute('y1', y1);
    line.setAttribute('x2', x2);
    line.setAttribute('y2', y2);
    line.classList.add('connection-line');
    line.dataset.linkId = linkId;
    return line;
}

function redrawAllLines() {
    let svgLayer = document.getElementById('connections-layer');
    svgLayer.innerHTML = '';

    for (let i = 0; i < links.length; i++) {
        let link = links[i];
        let sourceNote = findNoteById(link.sourceNoteId);
        let targetNote = findNoteById(link.targetNoteId);

        if (!sourceNote || !targetNote) {
            continue;
        }

        let sourceCenter = getNoteCenterFromData(sourceNote);
        let targetCenter = getNoteCenterFromData(targetNote);

        let line = createSvgLine(
            sourceCenter.x, sourceCenter.y,
            targetCenter.x, targetCenter.y,
            link.id
        );
        svgLayer.appendChild(line);
    }
}
