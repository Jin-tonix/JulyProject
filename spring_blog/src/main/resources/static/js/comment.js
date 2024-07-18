// DOMContentLoaded 이벤트 발생 시 fetchComments 함수를 호출하여 댓글을 불러옵니다.
document.addEventListener("DOMContentLoaded", function () {
    fetchComments();
});

// 현재 페이지의 블로그 ID를 추출하는 함수입니다.
function getBlogIdFromPage() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

// 서버에서 댓글 데이터를 가져오는 함수입니다.
function fetchComments() {
    const blogId = getBlogIdFromPage(); // 현재 페이지의 블로그 ID를 가져옵니다.

    // 해당 블로그의 댓글을 가져오기 위한 fetch 요청을 보냅니다.
    fetch(`/jinhee/postpage/${blogId}/comments`)
        .then(response => response.json()) // 응답 데이터를 JSON 형식으로 파싱합니다.
        .then(data => {
            const commentsList = document.getElementById('commentsList'); // 댓글 목록을 표시할 요소를 가져옵니다.
            commentsList.innerHTML = ''; // 댓글 목록을 초기화합니다.

            // 각 댓글 데이터마다 displayComment 함수를 호출하여 화면에 댓글을 표시합니다.
            data.forEach(comment => {
                displayComment(comment);
            });
        })
        .catch(error => console.error('댓글 불러오기 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
}

// 사용자가 입력한 댓글을 서버에 등록하는 함수입니다.
function submitComment() {
    const blogId = getBlogIdFromPage(); // 현재 페이지의 블로그 ID를 가져옵니다.
    const commentText = document.getElementById('commentText').value; // 입력된 댓글 내용을 가져옵니다.
    const commentDTO = { content: commentText }; // 댓글 데이터를 객체로 생성합니다.

    // 댓글을 등록하기 위한 POST 요청을 보냅니다.
    fetch(`/jinhee/postpage/${blogId}/comment`, {
        method: 'POST', // HTTP 메서드는 POST입니다.
        headers: {
            'Content-Type': 'application/json' // 요청 본문의 타입은 JSON 형식입니다.
        },
        body: JSON.stringify(commentDTO) // 댓글 데이터를 JSON 문자열로 변환하여 요청 본문에 포함합니다.
    })
        .then(response => response.json()) // 응답 데이터를 JSON 형식으로 파싱합니다.
        .then(newComment => {
            displayComment(newComment); // 등록된 댓글을 화면에 표시합니다.
            document.getElementById('commentText').value = ''; // 입력 필드를 초기화하여 사용자가 입력한 내용을 지웁니다.
        })
        .catch(error => console.error('댓글 등록 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
}

// 댓글을 수정하는 함수입니다.
function editComment(commentId, currentContent, isReply = false) {
    const newContent = prompt('댓글을 수정하세요:', currentContent); // 사용자에게 수정할 내용을 입력받습니다.
    if (newContent !== null) { // 사용자가 취소하지 않고 수정을 진행했을 경우
        const commentDTO = { content: newContent }; // 수정된 내용을 객체로 생성합니다.
        const url = isReply ? `/jinhee/postpage/comment/reply/${commentId}` : `/jinhee/postpage/comment/${commentId}`;

        // 수정된 내용을 서버에 전송하기 위한 PUT 요청을 보냅니다.
        fetch(url, {
            method: 'PUT', // HTTP 메서드는 PUT입니다.
            headers: {
                'Content-Type': 'application/json' // 요청 본문의 타입은 JSON 형식입니다.
            },
            body: JSON.stringify(commentDTO) // 수정된 댓글 데이터를 JSON 문자열로 변환하여 요청 본문에 포함합니다.
        })
            .then(response => {
                if (!response.ok) { // 요청이 실패한 경우 오류를 throw합니다.
                    throw new Error('댓글 수정 실패');
                }
                return response.json(); // 응답 데이터를 JSON 형식으로 파싱합니다.
            })
            .then(updatedComment => {
                const selector = isReply ? `.reply[data-reply-id="${commentId}"] p` : `.comment[data-comment-id="${commentId}"] p`;
                const commentElement = document.querySelector(selector); // 수정된 댓글을 화면에서 찾습니다.
                if (commentElement) {
                    commentElement.textContent = updatedComment.content; // 수정된 내용으로 화면을 업데이트합니다.
                } else {
                    console.error('DOM 요소를 찾을 수 없습니다.');
                }
            })
            .catch(error => console.error('댓글 수정 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
    }
}

// 댓글을 삭제하는 함수입니다.
function deleteComment(commentId, isReply = false) {
    if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) { // 사용자에게 삭제 여부를 묻습니다.
        const url = isReply ? `/jinhee/postpage/comment/reply/${commentId}` : `/jinhee/postpage/comment/${commentId}`;

        // 삭제할 댓글에 대한 DELETE 요청을 보냅니다.
        fetch(url, {
            method: 'DELETE' // HTTP 메서드는 DELETE입니다.
        })
            .then(() => {
                const selector = isReply ? `.reply[data-reply-id="${commentId}"]` : `.comment[data-comment-id="${commentId}"]`;
                const commentElement = document.querySelector(selector); // 삭제된 댓글을 화면에서 찾습니다.
                if (commentElement) {
                    commentElement.remove(); // 화면에서 삭제된 댓글을 제거합니다.
                } else {
                    console.error('DOM 요소를 찾을 수 없습니다.');
                }
            })
            .catch(error => console.error('댓글 삭제 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
    }
}

// 댓글을 화면에 표시하는 함수입니다.
function displayComment(comment) {

    const commentsList = document.getElementById('commentsList'); // 댓글 목록을 표시할 요소를 가져옵니다.

    // 댓글 요소를 생성합니다.
    const commentElement = document.createElement('div');
    commentElement.classList.add('comment'); // 클래스를 추가합니다.
    commentElement.dataset.commentId = comment.id; // 데이터 속성을 설정합니다.

    // 댓글 내용을 표시할 p 요소를 생성하고 내용을 설정합니다.
    const commentContent = document.createElement('p');
    commentContent.textContent = comment.content;
    commentElement.appendChild(commentContent); // 댓글 요소에 추가합니다.

    // 수정 버튼을 생성하고 클릭 시 editComment 함수를 호출하도록 설정합니다.
    const editButton = document.createElement('button');
    editButton.textContent = '수정';
    editButton.onclick = () => editComment(comment.id, comment.content); // 수정 버튼 클릭 시 editComment 함수를 호출합니다.
    commentElement.appendChild(editButton); // 댓글 요소에 추가합니다.

    // 삭제 버튼을 생성하고 클릭 시 deleteComment 함수를 호출하도록 설정합니다.
    const deleteButton = document.createElement('button');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = () => deleteComment(comment.id); // 삭제 버튼 클릭 시 deleteComment 함수를 호출합니다.
    commentElement.appendChild(deleteButton); // 댓글 요소에 추가합니다.

    // 대댓글 입력 폼을 생성합니다.
    const replyForm = document.createElement('div');
    replyForm.classList.add('reply-form'); // 클래스를 추가합니다.
    replyForm.innerHTML = `
        <textarea class="form-control" id="replyText-${comment.id}" rows="1" placeholder="대댓글을 입력하세요..." required></textarea>
        <button type="button" class="btn btn-primary" onclick="submitReply(${comment.id})">등록</button>
    `;
    commentElement.appendChild(replyForm); // 댓글 요소에 추가합니다.

    // 대댓글 목록을 표시할 요소를 생성합니다.
    const repliesList = document.createElement('div');
    repliesList.classList.add('replies-list'); // 클래스를 추가합니다.
    commentElement.appendChild(repliesList); // 댓글 요소에 추가합니다.

    // 기존 대댓글을 표시합니다.
    if (comment.replies && comment.replies.length > 0) {
        comment.replies.forEach(reply => displayReply(repliesList, reply)); // 대댓글을 화면에 표시합니다.
    }

    // 댓글 요소를 댓글 목록에 추가합니다.
    commentsList.appendChild(commentElement);
}
// 사용자가 입력한 대댓글을 서버에 등록하는 함수입니다.
function submitReply(commentId) {
    const replyText = document.getElementById(`replyText-${commentId}`).value.trim(); // 입력된 대댓글 내용을 가져옵니다.

    // 입력 값이 유효한지 확인합니다.
    if (replyText === '') {
        alert('대댓글 내용을 입력해 주세요.');
        return;
    }

    const replyDTO = { content: replyText }; // 대댓글 데이터를 객체로 생성합니다.

    // 대댓글을 등록하기 위한 POST 요청을 보냅니다.
    fetch(`/jinhee/postpage/comment/reply/${commentId}`, {
        method: 'POST', // HTTP 메서드는 POST입니다.
        headers: {
            'Content-Type': 'application/json' // 요청 본문의 타입은 JSON 형식입니다.
        },
        body: JSON.stringify(replyDTO) // 대댓글 데이터를 JSON 문자열로 변환하여 요청 본문에 포함합니다.
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('대댓글 등록 실패');
            }
            return response.json(); // 응답 데이터를 JSON 형식으로 파싱합니다.
        })
        .then(newReply => {
            const repliesList = document.querySelector(`.comment[data-comment-id="${commentId}"] .replies-list`);
            displayReply(repliesList, newReply); // 등록된 대댓글을 화면에 표시합니다.
            document.getElementById(`replyText-${commentId}`).value = ''; // 입력 필드를 초기화하여 사용자가 입력한 내용을 지웁니다.
        })
        .catch(error => console.error('대댓글 등록 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
}

// 대댓글을 수정하는 함수입니다.
function editReply(replyId, currentContent) {
    const newContent = prompt('대댓글을 수정하세요:', currentContent); // 사용자에게 수정할 내용을 입력받습니다.
    if (newContent !== null && newContent.trim() !== "") { // 사용자가 취소하지 않고 수정을 진행했을 경우
        const replyDTO = { content: newContent.trim() }; // 수정된 내용을 객체로 생성합니다.
        const url = `/jinhee/postpage/comment/reply/${replyId}`;

        // 수정된 내용을 서버에 전송하기 위한 PUT 요청을 보냅니다.
        fetch(url, {
            method: 'PUT', // HTTP 메서드는 PUT입니다.
            headers: {
                'Content-Type': 'application/json' // 요청 본문의 타입은 JSON 형식입니다.
            },
            body: JSON.stringify(replyDTO) // 수정된 대댓글 데이터를 JSON 문자열로 변환하여 요청 본문에 포함합니다.
        })
            .then(response => {
                if (!response.ok) { // 요청이 실패한 경우 오류를 throw합니다.
                    throw new Error('대댓글 수정 실패');
                }
                return response.json(); // 응답 데이터를 JSON 형식으로 파싱합니다.
            })
            .then(updatedReply => {
                const selector = `.reply[data-reply-id="${replyId}"] p`;
                const replyElement = document.querySelector(selector); // 수정된 대댓글을 화면에서 찾습니다.
                if (replyElement) {
                    replyElement.textContent = updatedReply.content; // 수정된 내용으로 화면을 업데이트합니다.
                } else {
                    console.error('DOM 요소를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('대댓글 수정 오류:', error); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
                alert('대댓글 수정 중 오류가 발생했습니다. 다시 시도해 주세요.'); // 사용자에게 오류 메시지를 알립니다.
            });
    } else {
        alert('대댓글 내용을 입력해 주세요.'); // 사용자에게 대댓글 내용을 입력하라고 알립니다.
    }
}

// 대댓글을 화면에 표시하는 함수입니다.
function displayReply(repliesList, reply) {
    // 대댓글 요소를 생성합니다.
    const replyElement = document.crea

// 대댓글을 삭제하는 함수입니다.
    function deleteReply(replyId) {
        if (confirm("정말로 이 대댓글을 삭제하시겠습니까?")) { // 사용자에게 삭제 여부를 묻습니다.
            const url = `/jinhee/postpage/comment/reply/${replyId}`;

            // 삭제할 대댓글에 대한 DELETE 요청을 보냅니다.
            fetch(url, {
                method: 'DELETE' // HTTP 메서드는 DELETE입니다.
            })
                .then(() => {
                    const selector = `.reply[data-reply-id="${replyId}"]`;
                    const replyElement = document.querySelector(selector); // 삭제된 대댓글을 화면에서 찾습니다.
                    if (replyElement) {
                        replyElement.remove(); // 화면에서 삭제된 대댓글을 제거합니다.
                    } else {
                        console.error('DOM 요소를 찾을 수 없습니다.');
                    }
                })
                .catch(error => console.error('대댓글 삭제 오류:', error)); // 오류 발생 시 콘솔에 오류 메시지를 출력합니다.
        }
    }
}