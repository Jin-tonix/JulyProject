document.addEventListener("DOMContentLoaded", function () {
    fetchComments();
});

function getBlogIdFromPage() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

function fetchComments() {
    const blogId = getBlogIdFromPage();
    fetch(`/jinhee/postpage/${blogId}/comments`)
        .then(response => response.json())
        .then(data => {
            const commentsList = document.getElementById('commentsList');
            commentsList.innerHTML = '';
            data.forEach(comment => {
                displayComment(comment);
            });
        })
        .catch(error => console.error('댓글 불러오기 오류:', error));
}

function submitComment(parentCommentId = null) {
    const blogId = getBlogIdFromPage();
    const commentText = document.getElementById('commentText').value;
    const commentDTO = {
        content: commentText,
        parentCommentId: parentCommentId // 부모 댓글 ID 추가
    };

    fetch(`/jinhee/postpage/${blogId}/comment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(commentDTO)
    })
        .then(response => response.json())
        .then(newComment => {
            displayComment(newComment);
            document.getElementById('commentText').value = ''; // 입력 필드 초기화
        })
        .catch(error => console.error('댓글 등록 오류:', error));
}

function editComment(commentId, currentContent) {
    const newContent = prompt('댓글을 수정하세요:', currentContent);
    if (newContent !== null) {
        const commentDTO = { content: newContent };

        fetch(`/jinhee/postpage/comment/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(commentDTO)
        })
            .then(response => response.json())
            .then(updatedComment => {
                const commentElement = document.querySelector(`.comment[data-comment-id="${updatedComment.id}"] p`);
                commentElement.textContent = updatedComment.content;
            })
            .catch(error => console.error('댓글 수정 오류:', error));
    }
}

function deleteComment(commentId) {
    if (confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
        fetch(`/jinhee/postpage/comment/${commentId}`, {
            method: 'DELETE'
        })
            .then(() => {
                const commentElement = document.querySelector(`.comment[data-comment-id="${commentId}"]`);
                commentElement.remove();
            })
            .catch(error => console.error('댓글 삭제 오류:', error));
    }
}

function displayComment(comment) {
    const commentsList = document.getElementById('commentsList');

    // 댓글 요소 생성
    const commentElement = document.createElement('div');
    commentElement.classList.add('comment');
    commentElement.dataset.commentId = comment.id;

    const commentContent = document.createElement('p');
    commentContent.textContent = comment.content;
    commentElement.appendChild(commentContent);

    // 수정 버튼 생성
    const editButton = document.createElement('button');
    editButton.textContent = '수정';
    editButton.onclick = () => editComment(comment.id, comment.content);
    commentElement.appendChild(editButton);

    // 삭제 버튼 생성
    const deleteButton = document.createElement('button');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = () => deleteComment(comment.id);
    commentElement.appendChild(deleteButton);

    // 대댓글 입력 폼과 버튼 생성
    const replyForm = document.createElement('form');
    replyForm.classList.add('reply-form');
    const replyInput = document.createElement('input');
    replyInput.type = 'text';
    replyInput.placeholder = '대댓글을 입력하세요...';
    const replyButton = document.createElement('button');
    replyButton.type = 'submit';
    replyButton.textContent = '답글 달기';

    replyForm.onsubmit = (event) => {
        event.preventDefault();
        submitComment(comment.id); // 부모 댓글의 ID를 넘겨 대댓글로 등록
    };

    replyForm.appendChild(replyInput);
    replyForm.appendChild(replyButton);
    commentElement.appendChild(replyForm);

    // 댓글 요소를 댓글 목록에 추가
    commentsList.appendChild(commentElement);
}
