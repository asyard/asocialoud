import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';
import userapi from './member-api'

Vue.use(Vuex);

/*const vuexLocalStorage = new VuexPersist({
    key: 'vuex', // The key to store the state on in the storage provider.
    storage: window.localStorage, // or window.sessionStorage or localForage
    // Function that passes the state and returns the state with only the objects you want to store.
    // reducer: state => state,
    // Function that passes a mutation and lets you decide if it should update the state in localStorage.
    // filter: mutation => (true)
})
*/

export default new Vuex.Store({
    state: {
        loginSuccess: false,
        loginError: false,
        userName: null,
        userPass: null
    },
    //plugins: [vuexLocalStorage.plugin],
    plugins: [createPersistedState()],
    mutations: {
        login_success(state, payload){
            state.loginSuccess = true;
            state.userName = payload.userName;
            state.userPass = payload.userPass;
        },
        login_error(state, payload){
            state.loginError = true;
            state.userName = payload.userName;
        },
        login_terminate(state, payload){
            state.loginSuccess = false;
            state.loginError = false;
            state.userName = payload.userName;
            state.userPass = null;
        }
    },
    actions: {
        login({commit}, {username, password}) {
            return new Promise((resolve, reject) => {
                //console.log("Accessing backend with user: '" + username);
                userapi.login(username, password)
                    .then(response => {
                        //alert("Response : '" + response.data + "' with Status code : " + response.status);
                        if(response.status == 200) {
                            //console.log("Login successful");
                            // place the loginSuccess state into our vuex store
                            commit('login_success', {
                                userName: username,
                                userPass: password
                            });
                        }
                        resolve(response)
                    })
                    .catch(error => {
                        alert(error);
                        // place the loginError state into our vuex store
                        commit('login_error', {
                            userName: username
                        });
                        reject("Invalid credentials!")
                    })
            })
        },
        logout({commit}) {
            commit('login_terminate', {
                //userName: username
            });
        }

    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})