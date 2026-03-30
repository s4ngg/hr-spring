/* ============================================
   학습용 웹페이지 - 최소 JavaScript
   기능: 모바일 메뉴 토글, 삭제 확인 모달, 폼 유효성 시각 피드백
   ============================================ */

/* --- 모바일 메뉴 토글 --- */
function toggleMenu() {
  var nav = document.querySelector('.nav');
  if (nav) {
    nav.classList.toggle('open');
  }
}

/* --- 삭제 확인 모달 --- */
function showDeleteModal() {
  var overlay = document.getElementById('deleteModal');
  if (overlay) {
    overlay.classList.add('show');
  }
}

function hideDeleteModal() {
  var overlay = document.getElementById('deleteModal');
  if (overlay) {
    overlay.classList.remove('show');
  }
}

function confirmDelete() {
  /* 실제 서버 연동 시 AJAX 요청으로 대체 */
  alert('게시글이 삭제되었습니다.');
  window.location.href = 'board.html';
}

/* --- 폼 필수 입력 확인 (시각 피드백 전용) --- */
function validateRequired(form) {
  var inputs = form.querySelectorAll('[required]');
  var isValid = true;

  inputs.forEach(function (input) {
    var errorMsg = input.parentElement.querySelector('.error-msg');
    if (!input.value.trim()) {
      input.classList.add('error');
      if (errorMsg) errorMsg.classList.add('show');
      isValid = false;
    } else {
      input.classList.remove('error');
      if (errorMsg) errorMsg.classList.remove('show');
    }
  });

  return isValid;
}

/* --- 비밀번호 확인 일치 체크 --- */
function checkPasswordMatch() {
  var pw = document.getElementById('password');
  var pwConfirm = document.getElementById('passwordConfirm');
  var errorMsg = pwConfirm
    ? pwConfirm.parentElement.querySelector('.error-msg')
    : null;

  if (pw && pwConfirm && pw.value && pwConfirm.value) {
    if (pw.value !== pwConfirm.value) {
      pwConfirm.classList.add('error');
      if (errorMsg) errorMsg.classList.add('show');
      return false;
    } else {
      pwConfirm.classList.remove('error');
      if (errorMsg) errorMsg.classList.remove('show');
      return true;
    }
  }
  return true;
}

/* --- 폼 제출 핸들러 (학습용 - 실제 서버 미연동) --- */
function handleFormSubmit(event, successMessage, redirectUrl) {
  event.preventDefault();
  var form = event.target;

  if (!validateRequired(form)) {
    return;
  }

  if (document.getElementById('passwordConfirm')) {
    if (!checkPasswordMatch()) {
      return;
    }
  }

  alert(successMessage);
  if (redirectUrl) {
    window.location.href = redirectUrl;
  }
}
