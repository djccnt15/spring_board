document.addEventListener("DOMContentLoaded", function() {
  const btnClasses = ["up-btn", "down-btn"];
  btnClasses.forEach(className => {
    // get all the buttons with the class associated with "up" or "down"
    const buttons = document.querySelectorAll(`.${className}`);
    buttons.forEach(button => {
      // find the corresponding form for the button
      const categoryId = button.dataset.categoryId;
      const form = document.querySelector(`form[data-category-id='${categoryId}'].${className}`);

      button.addEventListener("click", async function(event) {
        event.preventDefault();

        if (form) {
          const formData = new FormData(form);
          const method = form.querySelector('input[name="_method"]')?.value || form.method;
          try {
            const response = await fetch(form.action, {
              method: method,
              body: formData,
            });
            if (!response.ok) {
              const data = await response.json();
              throw new Error(`${data.message}`);
            };
            window.location.reload();
          } catch (error) {
            alert("오류가 발생했습니다:\n" + (error.message || "알 수 없는 오류"));
          };
        };
      });
    });
  });
});
