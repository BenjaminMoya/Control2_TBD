// userState.js
export const userState = {
    userLogged: null,
    setUser(user) {
        this.userLogged = user;
    },
    getUser() {
        return this.userLogged;
    },
    clearUser() {
        this.userLogged = null;
    }
};