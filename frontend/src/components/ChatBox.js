import React, {useEffect, useState} from "react";
import axios from "axios";
import "./ChatBox.css"

const ChatBox = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);

    const API_BASE_URL = process.env.REACT_APP_BACKEND_URL || "/api";

    // Save chat history to local storage
    useEffect(() => {
        const loadChatHistory = async () => {
            try {
                const response = await axios.get(`${API_BASE_URL}/chat/history`);
                console.log("Chat history loaded: ", response.data);
                const formattedMessages = response.data.map(msg => ({
                    text: msg.message, // Ensure correct mapping
                    sender: msg.sender.toLowerCase(), // Convert "User" / "Bot" to "user" / "bot"
                }));
                setMessages(formattedMessages);
            } catch (error) {
                console.error("Error fetching chat history: ", error);
            }
        };
        loadChatHistory();
    }, []);

    //Function to handle API call
    const fetchChatResponse = async (userMessage) => {
        setLoading(true);
        try {
            const response = await axios.post(`${API_BASE_URL}/chat`, {
                message: userMessage,
            });
            console.log("Chat response: ", response.data);
            const botMessage = response.data.response;
            //Append both user and bot messages to the chat box
            if(response.data && response.data.response) {
                setMessages((prevMessages) => [
                    ...prevMessages,
                    {text: userMessage, sender: "user"},
                    {text: botMessage, sender: "bot"},
                ]);
            } else {
                throw new Error("Invalid response from the server");
            }
        } catch (error) {
            console.error("Error fetching chat response: ", error);
            setMessages((prevMessages) => [
                ...prevMessages,
                { text: "Error fetching chat response. Try again!", sender: "bot"},
            ]);
            throw error;
        }
        setLoading(false)
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!input.trim()) return;
        await fetchChatResponse(input);
        setInput("");
    };

    return (
        <div className="chat-container">
            <div className="chat-box">
                {messages.map((msg, index) => (
                    <div key={index} className={`message ${msg.sender}  animate`}
                            style={{marginBottom: "10px" }}>
                        {msg.text}
                    </div>
                ))}
                {loading && <div className="loading">Thinking...</div>}
            </div>
            <form onSubmit={handleSubmit} className="chat-input">
                <input
                    type="text"
                    placeholder="Type a message..."
                    value={input}
                    onChange={(e) => setInput(e.target.value)}
                />
                <button type="submit" disabled={loading}>
                    {loading ? "..." : "Send"}
                </button>
            </form>
        </div>
    );
}

export default ChatBox;