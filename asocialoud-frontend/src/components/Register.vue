<template>
    <div class="register">
        <h1>Register</h1>

        <form @submit.prevent="createNewUser()">
            <input type="text" v-model="user.realName" placeholder="real name" autofocus> <br/>
            <input type="text" v-model="user.userName" placeholder="user name"><br/>
            <input type="text" placeholder="email" v-model="user.userEmail"><br/>
            <input type="password" placeholder="password" v-model="user.userPass"><br/>

            <b-btn variant="success" type="submit">Create User</b-btn>
        </form>

        <div v-if="showResponse">
            User created. Please <router-link to="/login">Sign in</router-link>
            <br/>
            <router-view/>
        </div>

        <div v-else-if="hasError"><h6>{{ errorTxt }}</h6></div>
    </div>
</template>

<script>
    import userapi from '../member-api';

    export default {
        name: "Register",
        data () {
            return {
                response: [],
                hasError: false,
                errorTxt: '',
                user: {
                    userName: '',
                    realName: '',
                    userEmail: '',
                    userPass: '',
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            createNewUser () {
                this.errorTxt = '';
                userapi.createNew(this.user.userName, this.user.realName, this.user.userEmail, this.user.userPass).then(response => {
                    if (response.data.status == 201) {
                        // JSON responses are automatically parsed.
                        this.showResponse = true;
                        this.hasError = false;
                        this.response = response.data;
                        this.user.id = response.data;
                    } else {
                        this.showResponse = false;
                        this.hasError = true;
                        this.errorTxt = response.data.data;
                    }

                })
                    .catch(e => {
                        this.showResponse = false;
                        this.hasError = true;
                        this.errorTxt = e;
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>