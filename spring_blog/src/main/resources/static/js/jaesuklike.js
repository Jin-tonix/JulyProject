function toggleLike(postId, userId) {
    fetch(`/jaesuk/post/${postId}/like?userId=${userId}`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            updateLikeUI(data.liked, data.likeCount);
        });
}

function updateLikeUI(isLiked, likeCount) {
    const likeButton = document.getElementById('likeButton');
    const likeCountElement = document.getElementById('likeCount');

    likeButton.classList.toggle('liked', isLiked);
    likeCountElement.textContent = likeCount;
}