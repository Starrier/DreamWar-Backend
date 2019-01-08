package org.starrier.dreamwar.security.config;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

/*@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    public static final String AUTHENTICATION_HEAD_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String API_ROOT_URL = "/api/**";

    @Autowired private final UserService userService;
    @Autowired private final RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private RestAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired private JwtAutenticationProvider jwtAutenticationProvider;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private TokenExtractor tokenExtractor;

    @Autowired private ObjectMapper objectMapper;

    @Autowired public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                AUTHENTICATION_URL,
                REFRESH_TOKEN_URL,
                "/console"
        );

        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll()

                .add()
                .authorizeRequest()
                .antMatchers(API_ROOT_URL).authenticated()

                .and()
                .addFilterBefore(new CusomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);


    }

}*/
