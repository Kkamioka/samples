const url = ''
const postItem = {
  template: '#template-post-item',
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onChangePost: function ($event) {
      this.$emit('update:done', $event.target.checked);
    },
  },
};

Vue.createApp({
  components: {
    'post-item': postItem,
  },
  mounted() {
    axios
      .get(`${url}/api/posts/`)
      .then((response) => (this.posts = response.data))
      .catch((error) => console.log(error));
  },
  data: function () {
    return {
      postTitle: '',
      postContent: '',
      posts: [],
    };
  },
  computed: {
    canCreatePost: function () {
      return this.postTitle !== '';
    },
    hasPosts: function () {
      return this.posts.length > 0;
    },
    resultPosts: function () {
      const hideDonePost = this.hideDonePost;
      return this.posts;
    },
  },
  watch: {
    posts: {
      handler: function (next) {
        window.localStorage.setItem('posts', JSON.stringify(next));
      },
      deep: true,
    },
  },
  methods: {
    createPost: function () {
      if (!this.canCreatePost) {
        return;
      }
      axios.defaults.withCredentials = true;
      axios
        .post(`${url}/api/posts/`, {
          title: this.postTitle,
          content: this.postContent,
        })
        .then((response) => this.posts.push(response.data))
        .catch((error) => console.log(error));

      this.postTitle = '';
      this.postContent = '';
    },
  },
  created: function () {
    const posts = window.localStorage.getItem('posts');

    if (posts) {
      this.posts = JSON.parse(posts);
    }
  },
}).mount('#app');
