# 🚀 AI-Powered Chatbot (React + Spring Boot + OpenAI API)

This is an **AI-powered chatbot** built using **React (Frontend), Spring Boot (Backend), and OpenAI API (GPT-3.5 Turbo)** to provide intelligent responses.  
💡 **This POC is fully functional and showcased on Upwork.**

---

## **✨ Features**
✔️ **Conversational AI** - Uses OpenAI API for intelligent chat responses  
✔️ **Full-Stack Architecture** - React frontend + Spring Boot backend  
✔️ **Error Handling & Logging** - Prevents API failures and improves debugging  
✔️ **Chat History Storage** - Saves conversations in PostgreSQL (Step 5)  
✔️ **Modern UI & Animations** - Clean, interactive chat experience  
✔️ **Local Storage Persistence** - Keeps chat history even after page refresh  

---

## **📌 Tech Stack**
| **Component**  | **Technology**  |
|---------------|---------------|
| Frontend | React.js (Hooks, Axios) |
| Backend | Java (Spring Boot) |
| Database | PostgreSQL (Persistent chat storage) |
| API Integration | OpenAI GPT-3.5 Turbo |
| Deployment (Upcoming) | Docker + Kubernetes |

---

## **🚀 POC Execution Timeline**
### **✅ Step 1: Project Setup**
- Initialized **Spring Boot Backend** (REST API)  
- Set up **React Frontend** (Chat UI with Hooks)  
- Configured **CORS for frontend-backend communication**  

### **✅ Step 2: API Call Structure (Frontend)**
- Implemented API call structure in **`ChatBox.js`** using `axios`  
- Connected **React ↔ Spring Boot Backend**  

### **✅ Step 3: Backend Core Development**
- Implemented **Spring Boot Chat API** (`/chat` endpoint)  
- Integrated **OpenAI API** for AI-generated responses  
- Fixed `.env` loading issue using `@PostConstruct`  

### **✅ Step 4: Frontend Integration**
- Designed **Chat UI with modern styling**  
- Added **smooth animations & message bubbles**  
- Implemented **loading indicators & error handling**  

### **✅ Step 5: Database Integration (PostgreSQL)**
- Configured **PostgreSQL in `application.yml`**  
- Created **`chat_messages` table** for storing chat history  
- Implemented **backend service layer to save & retrieve chat history**  
- Modified **React frontend to fetch chat history on load**  

### **🎯 Upwork Showcase Completed!**  
🔗 **Upwork Portfolio:** https://www.upwork.com/freelancers/dineshk06?p=1888872629719257088
---

## **🔜 Next Steps (Upcoming Features)**
### **Step 6: Deployment (Docker + Kubernetes)**
✅ **Create Dockerfile for Backend & Frontend**  
✅ **Setup `docker-compose.yml` to manage services**  
✅ **Deploy using Kubernetes (Minikube for local testing)**  
✅ **Automate CI/CD using GitHub Actions**  

---

## **📌 How to Run Locally**
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/chatbot-poc.git
cd chatbot-poc

2️⃣ Start the Backend (Spring Boot)
cd backend
mvn spring-boot:run

3️⃣ Start the Frontend (React)
cd frontend
npm install
npm start

4️⃣ Start PostgreSQL Database
Ensure PostgreSQL is running and execute:
CREATE DATABASE chatbot_db;

👨‍💻 Author
Dinesh Kumar
💼 Upwork: https://www.upwork.com/freelancers/dineshk06
🚀 Building AI & SaaS solutions


