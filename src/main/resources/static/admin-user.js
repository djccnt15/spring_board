function openBanModal(button) {
  var username = button.getAttribute("data-username");
  document.getElementById("banUserModalLabel").textContent = username + " 차단";

  var form = document.getElementById("banUserForm");
  var userId = button.getAttribute("data-user-id");
  form.action = `/admin/user/${userId}/ban`;
}

const banForm = document.getElementById("banUserForm");
banForm.addEventListener("submit", handleFormSubmit)

function openBlockModal(button) {
  var username = button.getAttribute("data-username");
  document.getElementById("blockUserModalLabel").textContent = username + " 정지";

  var form = document.getElementById("blockUserForm");
  var userId = button.getAttribute("data-user-id");
  form.action = `/admin/user/${userId}/block`;
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

async function submitForm(url, data, method) {
  const response = await fetch(url, { method, body: data });
  redirectUrl = response.headers.get("redirectUrl")
  if (redirectUrl) {
    return redirectUrl;
  }
  throw await response.json();
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
