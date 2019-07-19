<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <p>
            Welcome to the new social platform
        </p>

        Sign up from <router-link to="/register">here</router-link>. <br/>
        Already a member? <router-link to="/login">Sign in</router-link><br/>
        <a href="/register">ads</a>


        <button @click="listUsers">CALL Spring Boot REST backend service</button>

        <h3>{{ response }}</h3>


        <h3>Follow us</h3>
        <ul>
            <li><a href="https://twitter.com" target="_blank" rel="noopener">twitter</a></li>
            <li><a href="https://instagram.com" target="_blank" rel="noopener">instagram</a></li>
        </ul>
    </div>
</template>

<script>
    import userapi from '../member-api';

    export default {
        name: 'HelloWorld',
        props: {
            msg: String
        },

        data () {
            return {
                response: [],
                errors: [],
                user: {
                    lastName: '',
                    firstName: '',
                    id: 0
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },

        methods: {
            listUsers () {
                userapi.hello().then(response => {
                    // JSON responses are automatically parsed.
                    this.response = response.data;
                })
                    .catch(e => {
                        this.errors.push(e)
                    })
            }
        }
    }
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
