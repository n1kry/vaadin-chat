window.addEventListener('message', function (event) {
    if (event.data === 'getCurrentPage') {
        var currentPage = window.PDFViewerApplication.page;
        event.source.postMessage('currentPage:' + currentPage, event.origin);
    }
});