<template>
    <div class="user">
        <h1>Login</h1>

        <h3>Just some database interaction...</h3>

        <input type="text" v-model="user.userName" placeholder="user name">
        <input type="text" v-model="user.realName" placeholder="real name">

        <button @click="login()">Login</button>



        <div v-if="loginError">
            Login error
        </div>

        <div v-else-if="showResponse">
            Success!
        </div>

    </div>



</template>

<script>
    import userapi from '../member-api';

    export default {
        name: "Login",
        data () {
            return {
                response: [],
                errors:[],
                loginError: false,
                user: {
                    userName: '',
                    realName: '',
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            login () {
                userapi.login(this.user.userName, this.user.realName).then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                    this.showResponse = true;
                    this.loginError = false;
                    this.$router.push('/feed');
                })
                    .catch(e => {
                        this.loginError = true;
                        this.errors.push(e);
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>