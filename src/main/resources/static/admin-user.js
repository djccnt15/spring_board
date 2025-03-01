const btn_search = document.getElementById("btn-search");
const search_kw = document.getElementById("search-kw");

function performSearch() {
  document.getElementById("kw").value = search_kw.value;
  document.getElementById("size").value = document.getElementById("list-size").value;
  document.getElementById("page").value = 0; // 검색 시 0 페이지부터 조회
  document.getElementById("searchForm").submit();
}

btn_search.addEventListener("click", performSearch);
search_kw.addEventListener("keydown", (event) => {
  if (event.key === "Enter") performSearch();
});

function openBanModal(button) {
  const userId = button.getAttribute("data-user-id");
  const username = button.getAttribute("data-username");
  document.getElementById("banUserModalLabel").textContent = username + " 차단";

  const form = document.getElementById("banUserForm");
  const page = form.getAttribute("data-page");
  const size = form.getAttribute("data-size");
  let kw = form.getAttribute("data-keyword") || "";
  const actionUrl = `/admin/user/${userId}/ban?page=${page}&size=${size}&kw=${kw}`;
  form.action = actionUrl;
}

const banForm = document.getElementById("banUserForm");
banForm.addEventListener("submit", handleFormSubmit)

function openBlockModal(button) {
  const userId = button.getAttribute("data-user-id");
  const username = button.getAttribute("data-username");
  document.getElementById("blockUserModalLabel").textContent = username + " 정지";

  const form = document.getElementById("blockUserForm");
  const page = form.getAttribute("data-page");
  const size = form.getAttribute("data-size");
  let kw = form.getAttribute("data-keyword") || "";
  const actionUrl = `/admin/user/${userId}/block?page=${page}&size=${size}&kw=${kw}`;
  form.action = actionUrl;
}

const blockForm = document.getElementById("blockUserForm");
blockForm.addEventListener("submit", handleFormSubmit)

async function handleFormSubmit(event) {
  event.preventDefault();
  try {
    const form = event.target;
    const actionUrl = form.action;
    const method = form.querySelector('input[name="_method"]')?.value || form.method;
    const formData = new FormData(form);

    // Fetch request expecting JSON response
    const response = await fetch(actionUrl, {
      method: method.toUpperCase(),
      body: formData,
    });

    if (!response.ok) throw await response.json();

    const redirectUrl = response.headers.get("Location");
    if (redirectUrl) {
      window.location.replace(redirectUrl);
    } else {
      const data = await response.json();
      if (data.redirectUrl) {
        window.location.replace(data.redirectUrl);
      }
    }
  } catch (error) {
    console.error("Error occurred:", error);
  }
}

(() => {
  'use strict'
  var forms = document.querySelectorAll('.needs-validation')

  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
})()
