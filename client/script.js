const url = 'http://localhost:8080/api/reminders';

async function createReminder() {
  const nameInput = document.getElementById('name');
  const dateInput = document.getElementById('date');

  const name = nameInput.value;
  const date = dateInput.value;


  try {
    const response = await axios.post(`${url}/create`, { name, date });

    await showMessage(response.data);

    nameInput.value = '';
    dateInput.value = '';

    await fetchReminders();

  } catch (error) {
    const erroMessage = error.response.data;
    showMessage(erroMessage);
  }
}

async function deleteReminder(id) {
  try {
    await axios.delete(`${url}/delete/${id}`);
    await showMessage("Lembrete excluído com sucesso!");

    await fetchReminders();

  } catch (error) {
    const errorMessage = error.response.data;
    showMessage(errorMessage);
  }
}


function showReminders(reminders) {
  const listElement = document.getElementById('reminder-list');
  if (!listElement) {
    console.error('Elemento #reminder-list não encontrado');
    return;
  }
  listElement.innerHTML = '';

  reminders.forEach((group) => {
    const listItem = document.createElement('li');
    listItem.innerHTML = `<div class="date">${formatDate(group.groupDate)}</div>`;
    group.reminders.forEach((reminder) => {
      listItem.innerHTML += `<div class="reminder">
                                - ${reminder.name}
                                <button onclick="deleteReminder(${reminder.id})">X</button>
                              </div>`;
    });
    listElement.appendChild(listItem);
  });
}

// Função para buscar e exibir a lista de lembretes
async function fetchReminders() {
  try {
    const response = await axios.get(`${url}/list`);
    const reminders = response.data;
    showReminders(reminders);
  } catch (error) {
    const errorMessage = error.response.data;
    showReminders([]);
    showMessage(errorMessage);
  }
}

async function showMessage(message) {
  return new Promise((resolve) => {
    const errorElement = document.getElementById('error-message');
    if (errorElement) {
      errorElement.textContent = message;
    }
    showModal(message);

    setTimeout(() => {
      closeModal();
      resolve();
    }, 1500);
  });
}


function showModal(message) {
  const modal = document.getElementById('modal');
  const modalMessage = document.getElementById('modal-message');
  if (modal && modalMessage) {
    modalMessage.textContent = message;
    modal.style.display = 'block';
  }
}

function closeModal() {
  const modal = document.getElementById('modal');
  if (modal) {
    modal.style.display = 'none';
  }
}

function formatDate(date) {
  // Divide a data em ano, mês e dia
  const [year, month, day] = date.split('-');

  // Formata a data no formato desejado
  const formattedDate = `${day}-${month}-${year}`;

  return formattedDate;
}


// Função para inicializar o script após o carregamento da página
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('reminder-form');
  const errorElement = document.getElementById('error-message');
  const closeModalButton = document.getElementById('close-modal-button');

  if (form) {
    form.addEventListener('submit', (event) => {
      event.preventDefault();
      createReminder();
    });
  }

  // Remover a mensagem de erro ao digitar nos campos
  const nameInput = document.getElementById('name');
  const dateInput = document.getElementById('date');

  if (nameInput) {
    nameInput.addEventListener('input', () => {
      if (errorElement) {
        errorElement.textContent = '';
      }
    });
  }

  if (dateInput) {
    dateInput.addEventListener('input', () => {
      if (errorElement) {
        errorElement.textContent = '';
      }
    });
  }

  // Adicionar evento de clique ao botão de fechar o modal
  if (closeModalButton) {
    closeModalButton.addEventListener('click', closeModal);
  }

  fetchReminders();
});

